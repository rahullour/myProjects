class CreateContacts < ActiveRecord::Migration[7.0]
  def change
    create_table :contacts do |t|
      t.string :title
      t.string :first_name
      t.string :last_name
      t.text :address
      t.text :description
      t.string :email
      t.timestamps
    end
  end
end
