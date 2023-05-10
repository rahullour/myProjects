require "application_system_test_case"

class UserOrdersTest < ApplicationSystemTestCase
  setup do
    @user_order = user_orders(:one)
  end

  test "visiting the index" do
    visit user_orders_url
    assert_selector "h1", text: "User orders"
  end

  test "should create user order" do
    visit user_orders_url
    click_on "New user order"

    click_on "Create User order"

    assert_text "User order was successfully created"
    click_on "Back"
  end

  test "should update User order" do
    visit user_order_url(@user_order)
    click_on "Edit this user order", match: :first

    click_on "Update User order"

    assert_text "User order was successfully updated"
    click_on "Back"
  end

  test "should destroy User order" do
    visit user_order_url(@user_order)
    click_on "Destroy this user order", match: :first

    assert_text "User order was successfully destroyed"
  end
end
