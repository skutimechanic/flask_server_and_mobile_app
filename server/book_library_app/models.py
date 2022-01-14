from posixpath import split
from flask_sqlalchemy import BaseQuery
from book_library_app import db
from datetime import datetime
from marshmallow import Schema, fields, validate, ValidationError, validates
from werkzeug.datastructures import ImmutableDict


class Author(db.Model):
    __tablename__ = 'authors'
    id = db.Column(db.Integer, primary_key=True)
    first_name = db.Column(db.String(50), nullable=False)
    last_name = db.Column(db.String(50), nullable=False)
    birth_date = db.Column(db.Date, nullable=False)

    def __repr__(self):
        return f'<{self.__class__.__name__}>: {self.first_name} {self.last_name}'

    @staticmethod
    def get_schema_args(fields: str) -> dict:
        schema_args = {'many': True} # many=True informuje pakiet, ze przekazemy liste obiektow
        if fields:
            schema_args['only'] = [field for field in fields.split(',') if field in Author.__table__.columns]
        return schema_args

    @staticmethod
    def apply_order(query: BaseQuery, sort_keys: str) -> BaseQuery:
        if sort_keys:
            for key in sort_keys.split(','):
                desc = False
                if key.startswith('-'):
                    key = key[1:]
                    desc = True
                column_attr = getattr(Author, key, None)
                if column_attr is not None:
                    query = query.order_by(column_attr.desc()) if desc else query.order_by(column_attr)

        return query

    @staticmethod
    def apply_filter(query: BaseQuery, params: ImmutableDict) -> BaseQuery:
        for param, value in params.items():
            if param not in {'fields', 'sort'}:
                column_attr = getattr(Author, param, None)
                if column_attr is not None:
                    if param == 'birth_date':
                        try:
                            value = datetime.strptime(value, '%d-%m-%Y').date()
                        except ValueError:
                            continue
                    query = query.filter(column_attr == value)

        return query



class AuthorSchema(Schema):
    id = fields.Integer(dump_only=True) # id wykorzystywane tylko przy serializacji danych
    first_name = fields.String(required=True, validate=validate.Length(max=50)) # ustawiliśmy maks dlugosc i pole zawsze wymagania (szybsza walidacja)
    last_name = fields.String(required=True, validate=validate.Length(max=50))
    birth_date = fields.Date('%d-%m-%Y', required=True)

    @validates('birth_date') # sprawdzamy czy data nie jest rowna teraz
    def validate_birth_date(self, value):
        if value > datetime.now().date():
            raise ValidationError(f'Birth date must be lower than {datetime.now().date()}')


author_schema = AuthorSchema()