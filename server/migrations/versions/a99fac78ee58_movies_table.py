"""movies table

Revision ID: a99fac78ee58
Revises: 
Create Date: 2022-01-17 21:59:16.772912

"""
from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision = 'a99fac78ee58'
down_revision = None
branch_labels = None
depends_on = None


def upgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    op.create_table('movies',
    sa.Column('id', sa.Integer(), nullable=False),
    sa.Column('title', sa.String(length=50), nullable=False),
    sa.Column('description', sa.Text(), nullable=False),
    sa.Column('image_link', sa.String(length=100), nullable=True),
    sa.Column('rating_sum', sa.Integer(), nullable=True),
    sa.Column('number_of_votes', sa.Integer(), nullable=True),
    sa.Column('category', sa.Text(), nullable=True),
    sa.Column('director', sa.String(length=100), nullable=True),
    sa.Column('year', sa.Integer(), nullable=True),
    sa.Column('country', sa.String(length=100), nullable=True),
    sa.PrimaryKeyConstraint('id'),
    sa.UniqueConstraint('title')
    )
    # ### end Alembic commands ###


def downgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    op.drop_table('movies')
    # ### end Alembic commands ###
