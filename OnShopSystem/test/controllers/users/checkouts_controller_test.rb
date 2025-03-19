require "test_helper"

class Users::CheckoutsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @users_checkout = users_checkouts(:one)
  end

  test "should get index" do
    get users_checkouts_url
    assert_response :success
  end

  test "should get new" do
    get new_users_checkout_url
    assert_response :success
  end

  test "should create users_checkout" do
    assert_difference("Users::Checkout.count") do
      post users_checkouts_url, params: { users_checkout: {  } }
    end

    assert_redirected_to users_checkout_url(Users::Checkout.last)
  end

  test "should show users_checkout" do
    get users_checkout_url(@users_checkout)
    assert_response :success
  end

  test "should get edit" do
    get edit_users_checkout_url(@users_checkout)
    assert_response :success
  end

  test "should update users_checkout" do
    patch users_checkout_url(@users_checkout), params: { users_checkout: {  } }
    assert_redirected_to users_checkout_url(@users_checkout)
  end

  test "should destroy users_checkout" do
    assert_difference("Users::Checkout.count", -1) do
      delete users_checkout_url(@users_checkout)
    end

    assert_redirected_to users_checkouts_url
  end
end
