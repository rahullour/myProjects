require 'rails_helper'




RSpec.describe "CategoryTests", type: :request do
  describe "GET /categories/new" do

 
    setup do
      @admin_user = User.create(username: "johndoe", email: "johndoe@example.com",
                                password: "password", admin: true)
      sign_in_as(@admin_user)
    end


it "get new category form and create category" do
  get "/categories/new"
  assert_response :success
  assert_difference 'Category.count', 1 do
    post categories_path, params: { category: { name: "Sports"} }
    assert_response :redirect
  end
  follow_redirect!
  assert_response :success
  assert_match "Sports", response.body
end


it "get new category form and reject invalid category" do
  get "/categories/new"
  assert_response :success
  assert_no_difference 'Category.count' do
    post categories_path, params: { category: { name: " "} }    
  end
  assert_match "errors", response.body
end


end
end