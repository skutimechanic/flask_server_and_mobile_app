from book_library_app import db
from book_library_app.books import books_bp
from book_library_app.models import Book, BookSchema, book_schema
from book_library_app.utils import (apply_filter, apply_order, get_schema_args,
                                    validate_json_content_type, get_pagination)
from flask import jsonify
from webargs.flaskparser import use_args


@books_bp.route('/books', methods=['GET'])
def get_books():
    query = Book.query
    schema_args = get_schema_args(Book)
    query = apply_order(Book, query)
    query = apply_filter(Book, query)
    items, pagination = get_pagination(query, 'books.get_books')


    books = BookSchema(**schema_args).dump(items)

    return jsonify(
        {
            'success': True,
            'data': books, # wykona mappowanie na jsony
            'number_of_records': len(books),
            'pagination': pagination
        }
    )


@books_bp.route('/books/<int:book_id>', methods=['GET'])
def get_book(book_id: int):
    book = Book.query.get_or_404(book_id, description=f'Book with id {book_id} not found')

    return jsonify(
        {
            'success': True,
            'data': book_schema.dump(book)
        }
    )