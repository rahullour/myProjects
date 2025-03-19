class Product < ApplicationRecord
    validates_presence_of :product_name
    validates_presence_of :product_quantity
    validates_presence_of :color
    validates_presence_of :description
    validates_presence_of :weight
    validates_presence_of :mfg_date
    validates_presence_of :exp_date

    has_many :user_carts, dependent: :destroy
    has_many :user_orders, dependent: :destroy
end
