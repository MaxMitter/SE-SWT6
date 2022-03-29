namespace OrderManagement.Api.Controllers;

using Microsoft.AspNetCore.Mvc;
using OrderManagement.Dtos;
using MediatR;

[Route("api/[controller]")]
[ApiController]
[ApiConventionType(typeof(WebApiConventions))]
public class CustomersController : ControllerBase
{
  private readonly ILogger<CustomersController> logger;

  public CustomersController(ILogger<CustomersController> logger)
  {
    this.logger = logger ?? throw new ArgumentNullException(nameof(logger));
  }

  [HttpGet]
  public Task<IEnumerable<CustomerDto>> GetCustomers(Rating? rating)
  {
    return Task.FromResult<IEnumerable<CustomerDto>>(new List<CustomerDto>());
  }

  /// <summary>
  /// Provides detailed data for a customer with the specified ID.
  /// </summary>
  /// <param name="customerId">Unique ID of customer</param>
  [HttpGet("{customerId}")]
  public Task<ActionResult<CustomerDto>> GetCustomerById([FromRoute] Guid customerId)
  {
    return Task.FromResult<ActionResult<CustomerDto>>(NotFound());
  }

  [HttpPost]
  public Task<ActionResult<CustomerDto>> CreateCustomer(CustomerForCreationDto customer)
  {
    return Task.FromResult<ActionResult<CustomerDto>>(new StatusCodeResult(StatusCodes.Status501NotImplemented));
  }

  [HttpPut("{customerId}")]
  public Task<ActionResult> UpdateCustomer(Guid customerId, CustomerForUpdateDto customer)
  {
    return Task.FromResult<ActionResult>(new StatusCodeResult(StatusCodes.Status501NotImplemented));
  }

  [HttpDelete("{customerId}")]
  public Task<ActionResult> DeleteCustomer(Guid customerId)
  {
    return Task.FromResult<ActionResult>(new StatusCodeResult(StatusCodes.Status501NotImplemented));
  }
}
