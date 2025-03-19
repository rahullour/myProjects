Rails.application.routes.draw do
  resources :messages
  devise_for :users
  resources :chatrooms
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # Defines the root path route ("/")
   root "chatrooms#welcome"
   get "chatrooms", to: "chatrooms#index"
   delete "users/sign_out", to: "users#destroy"

  mount ActionCable.server, at: '/cable'

  end


