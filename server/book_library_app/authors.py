from book_library_app import app
from flask import jsonify
from book_library_app.models import Author, AuthorSchema, author_schema


@app.route('/api/v1/authors', methods=['GET'])
def get_authors():
    authors = Author.query.all()
    author_schema = AuthorSchema(many=True) # many=True informuje pakiet, ze przekazemy liste obiektow

    return jsonify(
        {
            'success': True,
            'data': author_schema.dump(authors), # wykona mappowanie na jsony
            'number_of_records': len(authors)
        }
    )


@app.route('/api/v1/authors/<int:author_id>', methods=['GET'])
def get_author(author_id: int):
    author = Author.query.get_or_404(author_id, description=f'Author with id {author_id} not found')

    return jsonify(
        {
            'success': True,
            'data': author_schema.dump(author)
        }
    )

 
@app.route('/api/v1/authors', methods=['POST'])
def create_author():
    return jsonify(
        {
            'success': True,
            'data': 'New author has been created'
        }), 201


@app.route('/api/v1/authors/<int:author_id>', methods=['PUT'])
def update_author(author_id: int):
    return jsonify(
        {
            'success': True,
            'data': f'Author with id: {author_id} has been updated'
        }
    )


@app.route('/api/v1/authors/<int:author_id>', methods=['DELETE'])
def delete_author(author_id: int):
    return jsonify(
        {
            'success': True,
            'data': f'Author with id: {author_id} has been deleted'
        }
    )
