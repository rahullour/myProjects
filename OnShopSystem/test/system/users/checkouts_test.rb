require "application_system_test_case"

class Users::CheckoutsTest < ApplicationSystemTestCase
  setup do
    @users_checkout = users_checkouts(:one)
  end

  test "visiting the index" do
    visit users_checkouts_url
    assert_selector "h1", text: "Checkouts"
  end

  test "should create checkout" do
    visit users_checkouts_url
    click_on "New checkout"

    click_on "Create Checkout"

    assert_text "Checkout was successfully created"
    click_on "Back"
  end

  test "should update Checkout" do
    visit users_checkout_url(@users_checkout)
    click_on "Edit this checkout", match: :first

    click_on "Update Checkout"

    assert_text "Checkout was successfully updated"
    click_on "Back"
  end

  test "should destroy Checkout" do
    visit users_checkout_url(@users_checkout)
    click_on "Destroy this checkout", match: :first

    assert_text "Checkout was successfully destroyed"
  end
end
