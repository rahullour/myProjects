class Video < ApplicationRecord
    has_many :comments, as: :commentable
end
