"""users table update

Revision ID: 5123359c9ca1
Revises: 925f18707993
Create Date: 2022-01-26 19:31:48.928781

"""
from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision = '5123359c9ca1'
down_revision = '925f18707993'
branch_labels = None
depends_on = None


def upgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    op.add_column('users', sa.Column('is_admin', sa.Boolean(), nullable=True))
    # ### end Alembic commands ###


def downgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    op.drop_column('users', 'is_admin')
    # ### end Alembic commands ###