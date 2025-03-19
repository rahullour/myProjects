require "application_system_test_case"

class ShoppingSessionsTest < ApplicationSystemTestCase
  setup do
    @shopping_session = shopping_sessions(:one)
  end

  test "visiting the index" do
    visit shopping_sessions_url
    assert_selector "h1", text: "Shopping sessions"
  end

  test "should create shopping session" do
    visit shopping_sessions_url
    click_on "New shopping session"

    click_on "Create Shopping session"

    assert_text "Shopping session was successfully created"
    click_on "Back"
  end

  test "should update Shopping session" do
    visit shopping_session_url(@shopping_session)
    click_on "Edit this shopping session", match: :first

    click_on "Update Shopping session"

    assert_text "Shopping session was successfully updated"
    click_on "Back"
  end

  test "should destroy Shopping session" do
    visit shopping_session_url(@shopping_session)
    click_on "Destroy this shopping session", match: :first

    assert_text "Shopping session was successfully destroyed"
  end
end
