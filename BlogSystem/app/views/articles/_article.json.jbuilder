json.extract! article, :id, :title, :des, :created_at, :updated_at
json.url article_url(article, format: :json)
