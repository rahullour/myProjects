require 'rails_helper'

RSpec.describe Category, type: :model do

  def category() 
  described_class.new(name: "some")
  end

  it "checks if categoty is valid" do
 
  expect(category).to be_valid
  end

  it "checks if name is present" do
    category = Category.new(name: "")
    expect(category).to_not be_valid
    end

  it "checks if name is present" do
    category = Category.new(name: "")
    expect(category).to_not be_valid
    end
  
    it "checks if name is unique" do
      category.save
      category2 = Category.new(name: "some")
      expect(category2).to_not be_valid
      end 

      it "checks if name is not too short" do
        
        category2 = Category.new(name: "s")
        expect(category2).to_not be_valid
        end 
      
      

end
