class ApplicationController < ActionController::Base
     before_action :authenticate_user!, except: :root
    # protect_from_forgery with: :exception
    # protect_from_forgery with: :null_session
    # protect_from_forgery with: :exception, prepend: true, except: :destroy
end




