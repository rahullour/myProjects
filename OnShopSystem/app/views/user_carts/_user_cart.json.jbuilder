json.extract! user_cart, :id, :created_at, :updated_at
json.url user_cart_url(user_cart, format: :json)
