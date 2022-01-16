from book_library_app import db
from book_library_app.authors import authors_bp
from book_library_app.models import Author, AuthorSchema, author_schema
from book_library_app.utils import (apply_filter, apply_order, get_pagination,
                                    get_schema_args, token_required,
                                    validate_json_content_type)
from flask import jsonify
from webargs.flaskparser import use_args


@authors_bp.route('/authors', methods=['GET'])
def get_authors():
    query = Author.query
    schema_args = get_schema_args(Author)
    query = apply_order(Author, query)
    query = apply_filter(Author, query)
    items, pagination = get_pagination(query, 'authors.get_authors')


    authors = AuthorSchema(**schema_args).dump(items)

    return jsonify(
        {
            'success': True,
            # 'data': author_schema.dump(authors), # wykona mappowanie na jsony
            'data': authors, # wykona mappowanie na jsony
            'number_of_records': len(authors),
            'pagination': pagination
        }
    )


@authors_bp.route('/authors/<int:author_id>', methods=['GET'])
def get_author(author_id: int):
    author = Author.query.get_or_404(author_id, description=f'Author with id {author_id} not found')

    return jsonify(
        {
            'success': True,
            'data': author_schema.dump(author)
        }
    )

 
@authors_bp.route('/authors', methods=['POST']) # kolejnosc wywolywania dekoratorów jest istotna
@token_required
@validate_json_content_type
@use_args(author_schema, error_status_code=400)
def create_author(user_id: int, args: dict):
    author = Author(**args)

    db.session.add(author)
    db.session.commit()

    return jsonify(
        {
            'success': True,
            'data': author_schema.dump(author)
        }), 201


@authors_bp.route('/authors/<int:author_id>', methods=['PUT'])
@token_required
@validate_json_content_type
@use_args(author_schema, error_status_code=400)
def update_author(user_id: int, args: dict, author_id: int): # wazna kolejnosc, najpierw dane z walidatora pozniej dopiero id
    author = Author.query.get_or_404(author_id, description=f'Author with id {author_id} not found')

    author.first_name = args['first_name']
    author.last_name = args['last_name']
    author.birth_date = args['birth_date']

    db.session.commit()

    return jsonify(
        {
            'success': True,
            'data': author_schema.dump(author)
        }
    )


@authors_bp.route('/authors/<int:author_id>', methods=['DELETE'])
@token_required
def delete_author(user_id: int, author_id: int):
    author = Author.query.get_or_404(author_id, description=f'Author with id {author_id} not found')

    db.session.delete(author)
    db.session.commit()

    return jsonify(
        {
            'success': True,
            'data': f'Author with id: {author_id} has been deleted'
        }
    )
