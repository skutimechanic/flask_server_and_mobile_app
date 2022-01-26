from flask import jsonify
from movie_library_app import db
from movie_library_app.models import (Movie, MovieSchema, MovieWithUserRate,
                                      MovieWithUserRateSchema, MoviesWithUserRateSchema, UserMovie,
                                      UserMovieSchema, movie_schema)
from movie_library_app.movies import movies_bp
from movie_library_app.utils import (abort, apply_filter, apply_order,
                                     token_required,
                                     validate_json_content_type)
from webargs.flaskparser import use_args


@movies_bp.route('/movies', methods=['GET'])
def get_movies():
    query = Movie.query
    query = apply_order(Movie, query)
    query = apply_filter(Movie, query)

    movies = MovieSchema(
        many=True, only=['image_link', 'title', 'year', 'category', 'rating_sum']).dump(query.all())

    return jsonify(
        {
            'success': True,
            'data': movies,
            'number_of_records': len(movies)
        }
    )


@movies_bp.route('/movies/<int:movie_id>', methods=['GET'])
def get_book(movie_id: int):
    movie = Movie.query.get_or_404(
        movie_id, description=f'Movie with id {movie_id} not found')

    return jsonify(
        {
            'success': True,
            'data': movie_schema.dump(movie)
        }
    )


@movies_bp.route('/user/movies', methods=['GET'])
@token_required
def get_user_movies(user_id: int):
    query = Movie.query
    query = apply_order(Movie, query)
    query = apply_filter(Movie, query)

    movies = []
    user_movies = UserMovie.query.filter(UserMovie.user_id == user_id).all()

    if user_movies is None:
        abort(404, description=f'Movies for user with {user_id} not found')

    for movie in query.all():
        contains = [x for x in user_movies if x.movie_id == movie.id]
        if contains:
            movie_with_user_rate = MovieWithUserRate(movie, contains[0].rate)
            movies.append(movie_with_user_rate)

    movies = MoviesWithUserRateSchema(many=True).dump(movies)

    return jsonify(
        {
            'success': True,
            'data': movies
        }
    )
    

@movies_bp.route('/user/movies/<int:movie_id>', methods=['GET'])
@token_required
def get_user_movie(user_id: int, movie_id: int):
    user_movie = UserMovie.query.filter(UserMovie.user_id == user_id).filter(UserMovie.movie_id == movie_id).first()

    if user_movie is None:
        abort(404, description=f'Movie with id {movie_id} for user with {user_id} not found')

    movie = Movie.query.get_or_404(
        movie_id, description=f'Movie with id {movie_id} not found')

    movie_with_user_rate = MovieWithUserRate(movie, user_movie.rate)

    movie = MovieWithUserRateSchema().dump(movie_with_user_rate)

    return jsonify(
        {
            'success': True,
            'data': movie
        }
    )
    

@movies_bp.route('/user/movies/<int:movie_id>', methods=['DELETE'])
@token_required
def delete_user_movie(user_id: int, movie_id: int):
    user_movie = UserMovie.query.filter(UserMovie.user_id == user_id).filter(UserMovie.movie_id == movie_id).first()

    if user_movie is None:
        abort(404, description=f'Movie with id {movie_id} for user with {user_id} not found')

    movie = Movie.query.get_or_404(
        movie_id, description=f'Movie with id {movie_id} not found')
        
    if user_movie.rate is not None:
        movie.rating_sum = movie.remove_rate(user_movie.rate)
        movie.number_of_votes -= 1


    db.session.delete(user_movie)
    db.session.commit()

    return jsonify(
        {
            'success': True,
            'data': f'Movie with id: {movie_id} has been deleted from your list'
        }
    )


@movies_bp.route('/user/movies', methods=['POST'])
@token_required
@validate_json_content_type
@use_args(UserMovieSchema(exclude=['user_id']), error_status_code=400)
def set_movie_rate(user_id: int, args: dict):
    new_rating = 0

    user_movie = UserMovie.query.filter(UserMovie.user_id == user_id).filter(
        UserMovie.movie_id == args['movie_id']).first()

    if user_movie is not None:
        abort(
            409, description=f'Movie with id {user_movie.movie_id} exist on your list')

    user_movie = UserMovie(user_id=user_id, **args)

    movie = Movie.query.filter(Movie.id == user_movie.movie_id).first()
    if movie is None:
        abort(
            404, description=f'Movie with id {user_movie.movie_id} not found')

    if user_movie.rate is not None:
        new_rating = movie.handle_rating(user_movie.rate)
        movie.rating_sum = new_rating
        movie.number_of_votes += 1
    else:
        new_rating = movie.rating_sum

    db.session.add(user_movie)
    db.session.commit()

    return jsonify(
        {
            'success': True,
            'data': new_rating
        }
    )


@movies_bp.route('/user/movies', methods=['PUT'])
@token_required
@validate_json_content_type
@use_args(UserMovieSchema(exclude=['user_id']), error_status_code=400)
def update_movie_rate(user_id: int, args: dict):
    user_movie = UserMovie.query.filter(UserMovie.user_id == user_id).filter(
        UserMovie.movie_id == args['movie_id']).first()

    if user_movie is None:
        abort(404, description=f'Movies for user with {user_id} not found')

    movie = Movie.query.get_or_404(
        args['movie_id'], description=f'Movie with id {args["movie_id"]} not found')

    if "rate" in args:
        rate = args['rate']
        new_rating = movie.handle_rating(args['rate'], user_movie.rate)
        if user_movie.rate is None:
            movie.number_of_votes += 1
    else:
        rate = None
        if user_movie.rate is not None:
            new_rating = movie.remove_rate(user_movie.rate)
            movie.number_of_votes -= 1
        else:
            new_rating = movie.rating_sum

    movie.rating_sum = new_rating
    user_movie.rate = rate
    db.session.commit()

    return jsonify(
        {
            'success': True,
            'data': new_rating
        }
    )
