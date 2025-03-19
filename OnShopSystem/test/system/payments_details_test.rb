require "application_system_test_case"

class PaymentsDetailsTest < ApplicationSystemTestCase
  setup do
    @payments_detail = payments_details(:one)
  end

  test "visiting the index" do
    visit payments_details_url
    assert_selector "h1", text: "Payments details"
  end

  test "should create payments detail" do
    visit payments_details_url
    click_on "New payments detail"

    click_on "Create Payments detail"

    assert_text "Payments detail was successfully created"
    click_on "Back"
  end

  test "should update Payments detail" do
    visit payments_detail_url(@payments_detail)
    click_on "Edit this payments detail", match: :first

    click_on "Update Payments detail"

    assert_text "Payments detail was successfully updated"
    click_on "Back"
  end

  test "should destroy Payments detail" do
    visit payments_detail_url(@payments_detail)
    click_on "Destroy this payments detail", match: :first

    assert_text "Payments detail was successfully destroyed"
  end
end
