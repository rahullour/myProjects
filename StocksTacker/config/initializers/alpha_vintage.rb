require 'alphavantage'

Alphavantage.configure do |config|
  config.api_key = Rails.application.credentials.alpha_vintage_client[:api_key]
end

