from marshmallow import Schema, fields, validate

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


movie_schema = MovieSchema()