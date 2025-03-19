require "test_helper"

class OderDetailsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @oder_detail = oder_details(:one)
  end

  test "should get index" do
    get oder_details_url
    assert_response :success
  end

  test "should get new" do
    get new_oder_detail_url
    assert_response :success
  end

  test "should create oder_detail" do
    assert_difference("OderDetail.count") do
      post oder_details_url, params: { oder_detail: {  } }
    end

    assert_redirected_to oder_detail_url(OderDetail.last)
  end

  test "should show oder_detail" do
    get oder_detail_url(@oder_detail)
    assert_response :success
  end

  test "should get edit" do
    get edit_oder_detail_url(@oder_detail)
    assert_response :success
  end

  test "should update oder_detail" do
    patch oder_detail_url(@oder_detail), params: { oder_detail: {  } }
    assert_redirected_to oder_detail_url(@oder_detail)
  end

  test "should destroy oder_detail" do
    assert_difference("OderDetail.count", -1) do
      delete oder_detail_url(@oder_detail)
    end

    assert_redirected_to oder_details_url
  end
end
