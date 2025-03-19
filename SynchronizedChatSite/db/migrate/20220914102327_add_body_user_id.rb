class AddBodyUserId < ActiveRecord::Migration[7.0]
  def change
    add_column :messages, :body, :text

    add_column :messages, :user_id, :integer
  end
end
