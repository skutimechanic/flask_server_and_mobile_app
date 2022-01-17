from movie_library_app import db
from movie_library_app.movies import movies_bp
from movie_library_app.models import Movie, MovieSchema, movie_schema
from movie_library_app.utils import (apply_filter, apply_order, get_schema_args)
from flask import jsonify, abort
from webargs.flaskparser import use_args


@movies_bp.route('/movies', methods=['GET'])
def get_movies():
    query = Movie.query
    schema_args = get_schema_args(Movie)
    query = apply_order(Movie, query)
    query = apply_filter(Movie, query)


    books = MovieSchema(**schema_args).dump(query.all())

    return jsonify(
        {
            'success': True,
            'data': books,
            'number_of_records': len(books)
        }
    )