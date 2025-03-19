require "application_system_test_case"

class UserCartsTest < ApplicationSystemTestCase
  setup do
    @user_cart = user_carts(:one)
  end

  test "visiting the index" do
    visit user_carts_url
    assert_selector "h1", text: "User carts"
  end

  test "should create user cart" do
    visit user_carts_url
    click_on "New user cart"

    click_on "Create User cart"

    assert_text "User cart was successfully created"
    click_on "Back"
  end

  test "should update User cart" do
    visit user_cart_url(@user_cart)
    click_on "Edit this user cart", match: :first

    click_on "Update User cart"

    assert_text "User cart was successfully updated"
    click_on "Back"
  end

  test "should destroy User cart" do
    visit user_cart_url(@user_cart)
    click_on "Destroy this user cart", match: :first

    assert_text "User cart was successfully destroyed"
  end
end
