Rails.application.routes.draw do
  resources :categories
  resources :sessions
  resources :users
  resources :articles
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # Defines the root path route ("/")
  # root "articles#index"

  root "articles#root"

      get 'signup', to: 'users#new'
      get 'login', to: 'sessions#new'
      post 'login', to: 'sessions#create'
      get 'logout', to: 'sessions#destroy'
      

end
