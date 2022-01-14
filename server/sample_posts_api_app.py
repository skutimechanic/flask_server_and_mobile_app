from urllib import response
from flask import Flask, jsonify, request


POSTS = [
    {
        'id': 1,
        'title': 'title 1',
        'text': 'text 1'
    },
    {
        'id': 2,
        'title': 'title 2',
        'text': 'text 2'
    },
    {
        'id': 3,
        'title': 'title 3',
        'text': 'text 3'
    },
    {
        'id': 4,
        'title': 'title 4',
        'text': 'text 4'
    },
]


app = Flask(__name__)


@app.route('/posts', methods=['GET', 'POST']) # domyslna metoda to GET
def items():
    response_data = {
        'success': True,
        'data': []
    }

    if request.method == 'GET':
        response_data['data'] = POSTS
        return jsonify(response_data)
    elif request.method == 'POST':
        data = request.json
        if 'id' not in data or 'title' not in data or 'text' not in data:
            response_data['success'] = False
            response_data['error'] = "Please provide all required information"
            response = jsonify(response_data)
            response.status_code = 400
        else:
            POSTS.append(data)
            response_data['data'] = POSTS
            response = jsonify(response_data)
            response.status_code = 201
        return response


@app.route('/posts/<int:post_id>') # przesylanie parametrow w pathie oraz konwersja na inta
def item(post_id):
    response_data = {
        'success': True,
        'data': []
    }

    # item = ''
    # for post in POSTS:
    #     if post['id'] == post_id:
    #         item = post

    # lub 

    try:
        item = [post for post in POSTS if post['id'] == post_id][0] # to nam zwroci liste, dlatego dodajemy  
    except IndexError:
        response_data['success'] = False
        response_data['error'] = 'Not found'
        response = jsonify(response_data)
        response.status_code = 404
    else:
        response_data['data'] = item
        response = jsonify(response_data)
    return response


# obsluga error tak zeby nie zwracalo domyslnego html 'Not found'
@app.errorhandler(404)
def not_found(error):
    response_data = {
        'success': False,
        'data': [],
        'error': 'Not found'
    }
    response = jsonify(response_data)
    response.status_code = 404
    return response


if __name__ == '__main__':
    app.run(debug=True)