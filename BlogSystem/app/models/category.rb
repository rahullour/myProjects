class Category < ApplicationRecord
    validates :name, presence: true, uniqueness: {case_sensitive: false}, length: {minimum: 3}
    has_many :article_categories
    has_many :articles, through: :article_categories
  end 