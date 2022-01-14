from flask import Flask
from config import Config
from flask_sqlalchemy import SQLAlchemy
from flask_migrate import Migrate 


app = Flask(__name__)
app.config.from_object(Config)

db = SQLAlchemy(app)
migrate = Migrate(app, db) # dzieki temu mamy automatyczna migracje 


from book_library_app import authors # import ponizej tworzenia app - wazne
from book_library_app import models
from book_library_app import db_manage_commands