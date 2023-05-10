class CreateProducts < ActiveRecord::Migration[7.0]
  def change
    create_table :products do |t|
      t.string :product_name
      t.string :description
      t.string :image
      t.integer :product_quantity
      t.string :color
      t.decimal :weight
      t.date :mfg_date
      t.date :exp_date
      t.integer :price

      t.timestamps
    end
  end
end
