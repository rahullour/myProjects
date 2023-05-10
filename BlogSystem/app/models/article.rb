class Article < ApplicationRecord
    belongs_to :user
    has_many :article_categories
    has_many :categories, through: :article_categories
    accepts_nested_attributes_for :article_categories
    validates :title, presence: true, length: {minimum: 1}
    validates :des, presence: true, length: {minimum: 1}
end
