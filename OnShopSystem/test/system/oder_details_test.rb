require "application_system_test_case"

class OderDetailsTest < ApplicationSystemTestCase
  setup do
    @oder_detail = oder_details(:one)
  end

  test "visiting the index" do
    visit oder_details_url
    assert_selector "h1", text: "Oder details"
  end

  test "should create oder detail" do
    visit oder_details_url
    click_on "New oder detail"

    click_on "Create Oder detail"

    assert_text "Oder detail was successfully created"
    click_on "Back"
  end

  test "should update Oder detail" do
    visit oder_detail_url(@oder_detail)
    click_on "Edit this oder detail", match: :first

    click_on "Update Oder detail"

    assert_text "Oder detail was successfully updated"
    click_on "Back"
  end

  test "should destroy Oder detail" do
    visit oder_detail_url(@oder_detail)
    click_on "Destroy this oder detail", match: :first

    assert_text "Oder detail was successfully destroyed"
  end
end
