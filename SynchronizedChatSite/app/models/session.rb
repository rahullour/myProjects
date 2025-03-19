class Session < ApplicationRecord
    validates :body, presence: true

end
