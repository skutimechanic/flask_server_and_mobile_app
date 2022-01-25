import json
from pathlib import Path
from movie_library_app import db
from movie_library_app.models import Movie, UserMovie
from movie_library_app.commands import db_manage_bp


def load_json_data(file_name: str) -> list:
    json_path = Path(__file__).parent.parent / 'samples' / file_name
    with open(json_path) as file:
        data_json = json.load(file)
    return data_json


@db_manage_bp.cli.group()
def db_manage():
    """Database management commands"""
    pass


@db_manage.command()
def add_data():
    """Add sample data to database"""
    try:
        data_json = load_json_data('movies.json')
        for item in data_json:
            movie = Movie(**item)
            db.session.add(movie)

        data_json = load_json_data('usermovies.json')
        for item in data_json:
            user_movie = UserMovie(**item)
            db.session.add(user_movie)
        db.session.commit()
        print('Data has been successfully added to database')
    except Exception as exc:
        print("Unexpected error: {}".format(exc))


@db_manage.command()
def remove_data():
    """Remove all data from the database"""
    try:
        db.session.execute('DELETE FROM movies')
        db.session.execute('ALTER TABLE movies AUTO_INCREMENT = 1')
        db.session.execute('DELETE FROM user_movies')
        db.session.execute('ALTER TABLE user_movies AUTO_INCREMENT = 1')
        db.session.commit()
        print('Data has been successfully removed to database')
    except Exception as exc:
        print("Unexpected error: {}".format(exc))