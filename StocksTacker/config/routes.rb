Rails.application.routes.draw do

  resources :user_stocks
  resources :welcomes 
  resources :users , :except => [:sign_in, :sign_up, :show, :destroy, :create] 
  devise_for :users
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # Defines the root path route ("/")

   root "welcomes#index"
   get "my_portfolio", to: "users#my_portfolio"
   get 'search_stock', to: 'stocks#search'
   get 'my_friends' , to: 'users#my_friends'
   get 'search_friend', to: 'users#search'
   post 'friendships/create', to: 'friendships#create'
   delete 'friendships/destroy', to: 'friendships#destroy'
   get 'users/edit', to: 'registrations#edit'
   get 'users/sign_in', to: 'sessions#new'
   get 'users/sign_up', to: 'registrations#new'
   get 'users/:id', to: 'users#show'
   

   
end
