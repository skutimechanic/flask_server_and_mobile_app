from distutils.log import debug
from flask import Flask, request


app = Flask(__name__) # katalog glowny


@app.route('/')
def index():
    # print(request.headers)
    # print(f'method: {request.method}')
    # print(f'path: {request.path}')
    # print(f'url: {request.url}')
    # print(request.headers['Authorization'])
    print(request.headers['Content-Type'])
    print(request.json.get('name'))
    return 'Hello from Flask!'


if __name__ == "__main__":
    app.run(debug=True) # dzieki debug=True mamy lepsze logi oraz kazda zmiana w kodzie triggeruje przebudowanie serweru