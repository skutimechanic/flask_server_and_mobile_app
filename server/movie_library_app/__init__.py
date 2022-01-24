
from flask import Flask
from config import Config
from flask_sqlalchemy import SQLAlchemy
from flask_migrate import Migrate 


db = SQLAlchemy()
migrate = Migrate()


def create_app(config_class=Config):
    app = Flask(__name__)
    app.config.from_object(config_class)

    db.init_app(app)
    migrate.init_app(app, db)

    from movie_library_app.commands import db_manage_bp
    from movie_library_app.errors import errors_bp
    from movie_library_app.movies import movies_bp
    from movie_library_app.auth import auth_bp
    app.register_blueprint(db_manage_bp)
    app.register_blueprint(errors_bp)
    app.register_blueprint(movies_bp, url_prefix='/api/v1')
    app.register_blueprint(auth_bp, url_prefix='/api/v1/auth')

    return app