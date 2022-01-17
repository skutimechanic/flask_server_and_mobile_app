from flask import Blueprint

errors_bp = Blueprint('erros', __name__)

from movie_library_app.errors import errors