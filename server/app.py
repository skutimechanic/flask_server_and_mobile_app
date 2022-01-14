from distutils.log import debug
from urllib import response
from flask import Flask, request, make_response, jsonify

# jsonify przeksztalca obiekt json do obiektu response, automatycznie ustawia Content-Type na application/json

app = Flask(__name__) # katalog glowny


@app.route('/')
def index():
    # print(request.headers)
    # print(f'method: {request.method}')
    # print(f'path: {request.path}')
    # print(f'url: {request.url}')
    # print(request.headers['Authorization'])
    # print(request.headers['Content-Type'])
    # print(request.json.get('name'))
    # return 'Hello from Flask!'

    # response = make_response([{'id': 1, 'title': 'Title'}]) # ({}, response_code) <- domyslnie 200
    # response.headers['Content-Type'] = 'application/json'
    # response = jsonify([{'id': 1, 'title': 'Title'}])
    response = jsonify({'error': 'Not found!'})
    response.status_code = 404 # ustawianie status code
    return response


if __name__ == "__main__":
    app.run(debug=True) # dzieki debug=True mamy lepsze logi oraz kazda zmiana w kodzie triggeruje przebudowanie serweru