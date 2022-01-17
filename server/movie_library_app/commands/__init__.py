from flask import Blueprint

db_manage_bp = Blueprint('db_manage_cmd', __name__, cli_group=None) # cli_group wskazuje do jakies gr przyporzadkowac komendy

from movie_library_app.commands import db_manage_commands