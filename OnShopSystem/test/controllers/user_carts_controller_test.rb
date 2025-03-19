require "test_helper"

class UserCartsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user_cart = user_carts(:one)
  end

  test "should get index" do
    get user_carts_url
    assert_response :success
  end

  test "should get new" do
    get new_user_cart_url
    assert_response :success
  end

  test "should create user_cart" do
    assert_difference("UserCart.count") do
      post user_carts_url, params: { user_cart: {  } }
    end

    assert_redirected_to user_cart_url(UserCart.last)
  end

  test "should show user_cart" do
    get user_cart_url(@user_cart)
    assert_response :success
  end

  test "should get edit" do
    get edit_user_cart_url(@user_cart)
    assert_response :success
  end

  test "should update user_cart" do
    patch user_cart_url(@user_cart), params: { user_cart: {  } }
    assert_redirected_to user_cart_url(@user_cart)
  end

  test "should destroy user_cart" do
    assert_difference("UserCart.count", -1) do
      delete user_cart_url(@user_cart)
    end

    assert_redirected_to user_carts_url
  end
end
