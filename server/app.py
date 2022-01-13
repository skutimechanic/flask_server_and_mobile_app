from distutils.log import debug
from flask import Flask


app = Flask(__name__) # katalog glowny


@app.route('/')
def index():
    return 'Hello from Flask!'


if __name__ == "__main__":
    app.run(debug=True) # dzieki debug=True mamy lepsze logi oraz kazda zmiana w kodzie triggeruje przebudowanie serweru