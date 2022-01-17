from flask import Blueprint

movies_bp = Blueprint('movies', __name__)

from movie_library_app.movies import movies