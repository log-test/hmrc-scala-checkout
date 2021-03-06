package shopping.cart.application.test

import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._

import services.ProductPrice._


class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "Application" should {

    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/bad-url")).map(status(_)) mustBe Some(NOT_FOUND)
    }

    "product service price for one apple" in {
      checkoutTotalPrice("apple") mustBe 0.60
    }
    
    "product service price for one orange" in {
      checkoutTotalPrice("orange") mustBe 0.25
    }

    "checkout must price for apple" in {
      route(app, FakeRequest(GET, "/checkout/apple")).map(contentAsString(_)) mustBe Some("0.60")
    }
    
    "checkout must price for orange" in {
      route(app, FakeRequest(GET, "/checkout/orange")).map(contentAsString(_)) mustBe Some("0.25")
    }
    
    "checkout must total for 2 apples for the price of 1" in {
      route(app, FakeRequest(GET, "/checkout/apple,apple,apple")).map(contentAsString(_)) mustBe Some("1.20")
    }
    
    "checkout must total for 3 oranges for the price of 2" in {
      route(app, FakeRequest(GET, "/checkout/orange,orange,orange")).map(contentAsString(_)) mustBe Some("0.50")
    }
    
    "checkout must total for multi apple and onrages" in {
      route(app, FakeRequest(GET, "/checkout/orange,orange,apple,apple")).map(contentAsString(_)) mustBe Some("1.10")
    }

  }
}
