require 'rails_helper'
require './spec/helpers/categories_helper'

RSpec.configure do |c|
  c.include CategoriesHelper
end


# is file name plural in requests , it is controller file else , request file

RSpec.describe "CategoriesControllerTests", type: :request do

  setup do
    @category = Category.create(name: "Sports")
    @admin_user = User.create(username: "johndoe", email: "johndoe@example.com",
                              password: "password", admin: true)
  end

  describe "get #index" do
    it "returns a 200 OK status" do
      get '/categories'
      expect(response).to have_http_status(200)
    end   
  end

    describe "post #create" do
    it "returns a 200 OK status" do
    post "/categories", :params => { :category => {:name => "My Widget"} }
    expect(response).to have_http_status(302)

    follow_redirect!
      expect(response).to have_http_status(:success)
       end
    end

  describe "get #new" do
    it "returns a 200 OK status" do

      sign_in_as(@admin_user)
      get '/categories/new'
      expect(response).to have_http_status(200)
  end
end

describe "get #show" do
it "returns a 200 OK status" do
  category = Category.create(name: "category_test")
  id = category.id
  get "/categories/#{id}"
  expect(response).to have_http_status(200)
end
end


describe "get/post #edit" do

    

    it "returns a 200 OK status" do
     
    category = Category.create(name: "category_test.(1..1000)")
    id = category.id
      
      get "/categories/#{id}/edit"
      expect(response).to have_http_status(302)
    end

    it "returns a 302 FOUND status" do
      category = Category.create(name: "category_test.(1..1000)")
       id = category.id
      
      patch "/categories/#{id}", :params => { :category => {:name => category.name} }
      expect(response).to have_http_status(302)

    follow_redirect!
      expect(response).to have_http_status(:success)
       end



 end

 describe "delete #delete" do


    it "returns a 302 FOUND status" do
      category = Category.create(name: "category_test.(1..1000)")
       id = category.id
      
      delete "/categories/#{id}", :params => { :category => {:name => category.name} }
      expect(response).to have_http_status(302)

    follow_redirect!
      expect(response).to have_http_status(:ok)
       end



 end
    
 it "should create category" do
  sign_in_as(@admin_user)
  assert_difference 'Category.count', 1 do
    post categories_url, params: { category: { name: "Travel" } }
    
  end
  expect(@category).to redirect_to(category_url(Category.last))
  # assert_redirected_to category_url(Category.last)
end

it "should not create category if not admin" do
  assert_no_difference 'Category.count' do
    post categories_url, params: { category: { name: "Travel" } }
  end
  expect(@category).to redirect_to(categories_url)
  # assert_redirected_to categories_url
end


end
