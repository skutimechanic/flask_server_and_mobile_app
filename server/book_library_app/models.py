from book_library_app import db
from datetime import datetime
from marshmallow import Schema, fields, validate, ValidationError, validates


class Author(db.Model):
    __tablename__ = 'authors'
    id = db.Column(db.Integer, primary_key=True)
    first_name = db.Column(db.String(50), nullable=False)
    last_name = db.Column(db.String(50), nullable=False)
    birth_date = db.Column(db.Date, nullable=False)

    def __repr__(self):
        return f'<{self.__class__.__name__}>: {self.first_name} {self.last_name}'


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