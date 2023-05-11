#!/usr/bin/env bash
# exit on error
set -o errexit

bundle install
bin/rails assets:clean
bin/rails assets:precompile
bin/rails db:rollback STEP=30
bin/rails db:migrate
bin/rails db:seed

