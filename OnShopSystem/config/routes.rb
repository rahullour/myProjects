Rails.application.routes.draw do
 
  resources :user_orders
  resources :user_carts
  resources :products
  resources :welcomes
  resource :two_factor_settings, except: [:index, :show]
      
  devise_for :users, controllers: {
    sessions: 'users/sessions',
    confirmations: 'users/confirmations',
    passwords: 'users/passwords',
    registrations: 'users/registrations',
    unlocks: 'users/unlocks',
    omniauth_callbacks: 'users/omniauth_callbacks'
  }
  devise_scope :user do
    authenticated :user do
      root to: 'products#root' , as: :authenticated_root
      get '/order_now', to: 'user_carts#order_now'
      post '/order_now', to: 'user_orders#create'
      post '/cancelorder', to: 'user_orders#cancelorder' 
      get '/edit_user_profile/:id', to: 'admin/admins#edit_user_profile', as: :edit_user_profile
      put '/update_user', to: 'admin/admins#update_user' 
      get '/list_users', to: 'users/registrations#index' 
      post '/delete_user', to: 'users/registrations#delete_user' 
      get '/profile_delete', to: 'users/registrations#profile_delete'
      get 'users/registrations/pdf', to: 'users/registrations#pdf'
      get 'users/registrations/user_pdf/:id', to: 'users/registrations#user_pdf', as: :user_pdf
      get '/phone_otp_reg', to: 'users/registrations#phone_otp_reg'
      get '/users_api', to: 'users/registrations#users_api'
      get '/orders_api', to: 'user_orders#orders_api'
      get '/products_api', to: 'products#products_api'
      post '/phone_otp_reg', to: 'users/registrations#phone_otp_verify_reg'
      get '/all_orders', to: 'user_orders#all_orders'
      post '/search_product', to: 'products#search_product', as: :product_search
      post '/search_cart', to: 'user_carts#search_cart', as: :search_cart
      post '/search_cart_order_now', to: 'user_carts#search_cart_order_now', as: :search_cart_order_now      
      post '/search_order', to: 'user_orders#search_order', as: :search_order
      post '/search_order_all_users', to: 'user_orders#search_order_all_users', as: :search_order_all_users
      post '/search_user', to: 'users/registrations#search_user', as: :search_user   
      get 'checkouts', to: 'users/checkouts#create', :defaults => {:format => 'html'}  
      get '/pay_success', to: 'user_orders#pay_success', as: :pay_success   
      get '/delete_two_factor', to: 'two_factor_settings#destroy', as: :delete_two_factor  
    end
  
    unauthenticated do
      root to: 'welcomes#root' , as: :unauthenticated_root
      get '/phone_otp', to: 'users/sessions#phone_otp'
      post '/phone_otp', to: 'users/sessions#phone_otp_verify'
      get '/phone_otp_reg', to: 'users/registrations#phone_otp_reg'
      post '/phone_otp_reg', to: 'users/registrations#phone_otp_verify_reg'
      
    end
    get '/about_us', to: 'users/registrations#about', as: :about_us
    get '/contact_us', to: 'users/registrations#contact', as: :contact_us
    post '/contact_submit', to: 'users/registrations#contact_submit', as: :contact_submit
  end

 
end
