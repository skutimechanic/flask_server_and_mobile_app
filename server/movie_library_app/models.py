from datetime import datetime, timedelta

import jwt
from flask import current_app
from marshmallow import Schema, fields, validate
from sqlalchemy import null
from werkzeug.security import check_password_hash, generate_password_hash

from movie_library_app import db


class Movie(db.Model):
    __tablename__ = 'movies'
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(50), nullable=False, unique=True)
    description = db.Column(db.Text, nullable=False)
    image_link = db.Column(db.String(100))
    rating_sum = db.Column(db.Integer)
    number_of_votes = db.Column(db.Integer)
    category = db.Column(db.Text)
    director = db.Column(db.String(100))
    year = db.Column(db.Integer)
    country = db.Column(db.String(100))

    def __repr__(self):
        return f'{self.title} - {self.description}'

    def handle_rating(self, rate, old_rate=None) -> int:
        if old_rate is None or old_rate is null:
            return (self.rating_sum*self.number_of_votes+rate)//(self.number_of_votes+1)
        else:
            rating = self.rating_sum*self.number_of_votes-old_rate
            return (rating+rate)//(self.number_of_votes)
        
    def remove_rate(self, old_rate) -> int:
        rating = self.rating_sum*self.number_of_votes-old_rate
        return rating//(self.number_of_votes-1)


class User(db.Model):
    __tablename__ = 'users'
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(255), nullable=False,
                         unique=True, index=True)
    email = db.Column(db.String(255), nullable=False, unique=True)
    password = db.Column(db.String(255), nullable=False)
    creation_date = db.Column(db.DateTime, default=datetime.utcnow)

    @staticmethod
    def generate_hashed_password(password: str) -> str:
        return generate_password_hash(password)

    def generate_jwt(self) -> str:
        payload = {
            'user_id': self.id,
            'exp': datetime.utcnow() + timedelta(minutes=current_app.config.get('JWT_EXPIRED_MINUTES', 30))
        }

        return jwt.encode(payload, current_app.config.get('SECRET_KEY'))

    def is_password_valid(self, password) -> bool:
        return check_password_hash(self.password, password)


class UserMovie(db.Model):
    __tablename__ = 'user_movies'
    id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.Integer, nullable=False)
    movie_id = db.Column(db.Integer, nullable=False)
    rate = db.Column(db.Integer)
   

class MovieWithUserRate():

    def __init__(self, movie, rate):
        self.movie = movie
        self.rate = rate


class MovieSchema(Schema):
    id = fields.Integer(dump_only=True)
    title = fields.String(required=True, validate=validate.Length(max=50))
    description = fields.String(required=True)
    image_link = fields.String()
    rating_sum = fields.Integer()
    number_of_votes = fields.Integer()
    category = fields.String()
    director = fields.String()
    year = fields.Integer(max=4)
    country = fields.String()
    isbn = fields.Integer(required=True)
    number_of_pages = fields.Integer(required=True)
    description = fields.String()


class MoviesWithUserRateSchema(Schema):
    movie = fields.Nested(lambda: MovieSchema(only=['image_link', 'title', 'year', 'category', 'rating_sum']))
    rate = fields.Integer()


class MovieWithUserRateSchema(Schema):
    movie = fields.Nested(lambda: MovieSchema())
    rate = fields.Integer()


class UserSchema(Schema):
    id = fields.Integer(dump_only=True)
    username = fields.String(required=True, validate=validate.Length(max=255))
    email = fields.Email(required=True)
    password = fields.String(
        required=True, load_only=True, validate=validate.Length(min=6, max=255))
    creation_date = fields.DateTime(dump_only=True)


class UserPasswordUpdateSchema(Schema):
    current_password = fields.String(
        required=True, load_only=True, validate=validate.Length(min=6, max=255))
    new_password = fields.String(
        required=True, load_only=True, validate=validate.Length(min=6, max=255))


class UserMovieSchema(Schema):
    user_id = fields.Integer(required=True)
    movie_id = fields.Integer(required=True)
    rate = fields.Integer()


movie_schema = MovieSchema()
user_schema = UserSchema()
user_password_update_schema = UserPasswordUpdateSchema()
