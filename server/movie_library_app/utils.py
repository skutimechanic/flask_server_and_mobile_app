import re
import jwt

from functools import wraps
from typing import Tuple

from flask import request, url_for, current_app, abort
from flask_sqlalchemy import BaseQuery, DefaultMeta
from sqlalchemy.orm.attributes import InstrumentedAttribute
from sqlalchemy.sql.expression import BinaryExpression
from werkzeug.exceptions import UnsupportedMediaType

COMPARISON_OPERATORS_RE = re.compile(r'(.*)\[(gte|gt|lte|lt)\]')


def validate_json_content_type(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        data = request.get_json()
        if data is None:
            raise UnsupportedMediaType('Content type must be application/json')
        return func(*args, **kwargs)
    return wrapper


def token_required(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        token = None
        auth = request.headers.get('Authorization')
        if auth:
            token = auth.split(' ')[1]
        if token is None:
            abort(401, description='Missing token. Please login or register')

        try:
            payload = jwt.decode(token, current_app.config.get('SECRET_KEY'), algorithms=['HS256'])
        except jwt.ExpiredSignatureError:
            abort(401, description='Expired token. Please login to get new token')
        except jwt.InvalidTokenError:
            abort(401, description='Invalid token. Please login or register')
        else:
            return func(payload['user_id'], *args, **kwargs)
    return wrapper


def get_schema_args(model: DefaultMeta) -> dict:
    # many=True informuje pakiet, ze przekazemy liste obiektow
    schema_args = {'many': True}
    fields = request.args.get('fields')
    if fields:
        schema_args['only'] = [field for field in fields.split(
            ',') if field in model.__table__.columns]
    return schema_args


def apply_order(model: DefaultMeta, query: BaseQuery) -> BaseQuery:
    sort_keys = request.args.get('sort')
    if sort_keys:
        for key in sort_keys.split(','):
            desc = False
            if key.startswith('-'):
                key = key[1:]
                desc = True
            column_attr = getattr(model, key, None)
            if column_attr is not None:
                query = query.order_by(
                    column_attr.desc()) if desc else query.order_by(column_attr)

    return query


def _get_filter_argument(column_name: InstrumentedAttribute, value: str, operator: str) -> BinaryExpression:
    operator_mapping = {
        '==': column_name == value,
        'gte': column_name >= value,
        'gt': column_name > value,
        'lte': column_name <= value,
        'lt': column_name < value
    }
    return operator_mapping[operator]


def apply_filter(model: DefaultMeta, query: BaseQuery) -> BaseQuery:
    for param, value in request.args.items():
        if param not in {'fields', 'sort', 'page', 'limit'}:
            operator = '=='
            match = COMPARISON_OPERATORS_RE.match(param)
            if match is not None:
                param, operator = match.groups()
            column_attr = getattr(model, param, None)
            if column_attr is not None:
                value = model.additional_validation(param, value)
                if value is None:
                    continue
                filter_argument = _get_filter_argument(
                    column_attr, value, operator)
                query = query.filter(filter_argument)

    return query