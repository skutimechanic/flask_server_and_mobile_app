from movie_library_app import db
from movie_library_app.movies import movies_bp
from movie_library_app.models import Movie, MovieSchema, UserMovie, movie_schema
from movie_library_app.utils import (apply_filter, apply_order, get_schema_args, token_required, abort)
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


@movies_bp.route('/user/movies', methods=['GET'])
@token_required
def get_user_movies(user_id: int):
    movies = []
    user_movies = UserMovie.query.filter(UserMovie.user_id == user_id).all()

    if user_movies is None:
        abort(409, description=f'Movies for user with {user_id} not found')

    for item in user_movies:
        movie = Movie.query.filter(Movie.id == item.movie_id).first()
        if movie is None:
            abort(409, description=f'Movie with id {item.movie_id} not found')
        
        movies.append(movie)

    movies = MovieSchema(many=True).dump(movies)

    return jsonify(
        {
            'success': True,
            'data': movies
        }
    )