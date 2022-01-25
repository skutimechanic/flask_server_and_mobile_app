from movie_library_app import db
from movie_library_app.movies import movies_bp
from movie_library_app.models import Movie, MovieSchema, movie_schema
from movie_library_app.utils import (apply_filter, apply_order, get_schema_args)
from flask import jsonify


@movies_bp.route('/movies', methods=['GET'])
def get_movies():
    query = Movie.query
    schema_args = get_schema_args(Movie)
    query = apply_order(Movie, query)
    query = apply_filter(Movie, query)


    movies = MovieSchema(**schema_args).dump(query.all())

    return jsonify(
        {
            'success': True,
            'data': movies,
            'number_of_records': len(movies)
        }
    )


@movies_bp.route('/movies/<int:movie_id>', methods=['GET'])
def get_book(movie_id: int):
    movie = Movie.query.get_or_404(movie_id, description=f'Movie with id {movie_id} not found')

    return jsonify(
        {
            'success': True,
            'data': movie_schema.dump(movie)
        }
    )