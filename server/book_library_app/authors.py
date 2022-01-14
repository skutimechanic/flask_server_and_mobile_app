from dbm import dumb
from book_library_app import app, db
from webargs.flaskparser import use_args
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
@use_args(author_schema)
def create_author(args: dict):
    author = Author(**args)

    db.session.add(author)
    db.session.commit()

    return jsonify(
        {
            'success': True,
            'data': author_schema.dump(author)
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
