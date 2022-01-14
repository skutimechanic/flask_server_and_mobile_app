from flask import Flask
from config import Config


app = Flask(__name__)
app.config.from_object(Config)


from book_library_app import authors # import ponizej tworzenia app - wazne